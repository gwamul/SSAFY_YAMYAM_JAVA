package com.ssafy.prj.model.dao;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.ssafy.prj.model.dto.ChallengeDto;
import com.ssafy.prj.model.dto.DayPlanDto;
import com.ssafy.prj.model.dto.MealItemDto;

/**
 * 챌린지 데이터를 CSV 파일로 저장하는 DAO 구현체.
 *
 * 저장 포맷 (challenges.csv):
 *   id, name, difficulty, duration, targetCalories, mealPlansJson
 *
 * mealPlansJson 은 CSV 내에서 큰따옴표로 감싼 간단한 형태로 저장합니다.
 * 형식: [d|meal:name=kcal;name=kcal|meal:name=kcal;...]
 * 예  : [1|B:국밥=137;김치=30|L:샐러드=80|D:닭가슴살=165]
 *
 * ─ DB 전환 가이드 ──────────────────────────────────────────────────
 * ChallengeDaoDbImpl 을 작성하고 ChallengeServiceImpl 생성자에서
 *   dao = ChallengeDaoDbImpl.getInstance();
 * 로 교체하기만 하면 됩니다.
 */
public class ChallengeDaoFileImpl implements ChallengeDao {

    // ── 싱글톤 ───────────────────────────────────────────────────────
    private static ChallengeDao instance;

    private ChallengeDaoFileImpl() {}

    public static synchronized ChallengeDao getInstance() {
        if (instance == null) instance = new ChallengeDaoFileImpl();
        return instance;
    }

    // ── 파일 경로 (FoodContextListener 에서 주입) ──────────────────
    private String csvPath;
    private final AtomicInteger idSeq = new AtomicInteger(0);

    /** 서버 기동 시 ChallengeContextListener 에서 호출 */
    public void init(String csvPath) {
        this.csvPath = csvPath;
        File f = new File(csvPath);
        if (!f.exists()) {
            // 파일 없으면 헤더만 작성
            try (PrintWriter pw = new PrintWriter(
                    new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
                pw.println("id,name,difficulty,duration,targetCalories,mealPlansEncoded");
            } catch (IOException e) {
                System.err.println("[ChallengeDaoCsvImpl] 파일 생성 실패: " + e.getMessage());
            }
        } else {
            // 기존 최대 ID 파악 (재시작 후 ID 중복 방지)
            List<ChallengeDto> all = selectAll();
            all.stream().mapToInt(ChallengeDto::getId).max()
               .ifPresent(max -> idSeq.set(max));
        }
        System.out.println("[ChallengeDaoCsvImpl] 초기화 완료: " + csvPath);
    }

    // ── ChallengeDao 구현 ────────────────────────────────────────────

    @Override
    public synchronized List<ChallengeDto> selectAll() {
        List<ChallengeDto> list = new ArrayList<>();
        File f = new File(csvPath);
        if (!f.exists()) return list;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(f), StandardCharsets.UTF_8))) {
            br.readLine(); // 헤더 스킵
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                ChallengeDto dto = decode(line);
                if (dto != null) list.add(dto);
            }
        } catch (IOException e) {
            System.err.println("[ChallengeDaoCsvImpl] 읽기 실패: " + e.getMessage());
        }
        return list;
    }

    @Override
    public ChallengeDto selectById(int id) {
        return selectAll().stream()
                .filter(c -> c.getId() == id)
                .findFirst().orElse(null);
    }

    @Override
    public synchronized void insert(ChallengeDto challenge) {
        challenge.setId(idSeq.incrementAndGet());
        File f = new File(csvPath);
        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(f, true), StandardCharsets.UTF_8))) {
            pw.println(encode(challenge));
        } catch (IOException e) {
            System.err.println("[ChallengeDaoCsvImpl] 저장 실패: " + e.getMessage());
        }
    }

    @Override
    public synchronized void delete(int id) {
        List<ChallengeDto> all = selectAll();
        all.removeIf(c -> c.getId() == id);
        rewrite(all);
    }

    // ── 인코딩/디코딩 헬퍼 ──────────────────────────────────────────

    /**
     * ChallengeDto → CSV 한 행 문자열
     * 형식: id,name,difficulty,duration,targetCalories,"[인코딩된 식단]"
     */
    private String encode(ChallengeDto c) {
        StringBuilder sb = new StringBuilder();
        sb.append(c.getId()).append(',');
        sb.append(escapeCsv(c.getName())).append(',');
        sb.append(escapeCsv(c.getDifficulty())).append(',');
        sb.append(c.getDuration()).append(',');
        sb.append(c.getTargetCalories()).append(',');
        sb.append('"').append(encodeMealPlans(c.getMealPlans())).append('"');
        return sb.toString();
    }

    /**
     * CSV 한 행 문자열 → ChallengeDto
     */
    private ChallengeDto decode(String line) {
        try {
            // 마지막 컬럼(큰따옴표로 감싼 mealPlans)을 분리
            int lastComma = findLastFieldStart(line);
            String mainPart  = line.substring(0, lastComma);
            String mealPart  = line.substring(lastComma + 1).replaceAll("^\"|\"$", "");

            String[] cols = mainPart.split(",", -1);
            if (cols.length < 5) return null;

            ChallengeDto dto = new ChallengeDto();
            dto.setId(Integer.parseInt(cols[0].trim()));
            dto.setName(unescapeCsv(cols[1]));
            dto.setDifficulty(unescapeCsv(cols[2]));
            dto.setDuration(Integer.parseInt(cols[3].trim()));
            dto.setTargetCalories(Integer.parseInt(cols[4].trim()));
            dto.setMealPlans(decodeMealPlans(mealPart));
            return dto;
        } catch (Exception e) {
            return null;
        }
    }

    /** mealPlans 인코딩: [d|B:name=kcal;name=kcal|L:...|D:...]$ 형태로 직렬화 */
    private String encodeMealPlans(List<DayPlanDto> plans) {
        if (plans == null || plans.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (DayPlanDto p : plans) {
            if (sb.length() > 0) sb.append('$');
            sb.append(p.getDay()).append('|');
            sb.append("B:").append(encodeMeals(p.getBreakfast())).append('|');
            sb.append("L:").append(encodeMeals(p.getLunch())).append('|');
            sb.append("D:").append(encodeMeals(p.getDinner()));
        }
        return sb.toString();
    }

    private String encodeMeals(List<MealItemDto> meals) {
        if (meals == null || meals.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (MealItemDto m : meals) {
            if (sb.length() > 0) sb.append(';');
            // 이름의 특수문자 대비: '=' ':' '|' '$' ';' 은 %(hex) 인코딩
            sb.append(urlEncode(m.getName())).append('=').append(m.getKcal());
        }
        return sb.toString();
    }

    private List<DayPlanDto> decodeMealPlans(String encoded) {
        List<DayPlanDto> result = new ArrayList<>();
        if (encoded == null || encoded.isBlank()) return result;
        for (String dayStr : encoded.split("\\$")) {
            String[] parts = dayStr.split("\\|");
            if (parts.length < 4) continue;
            DayPlanDto dp = new DayPlanDto();
            dp.setDay(Integer.parseInt(parts[0].trim()));
            dp.setBreakfast(decodeMeals(parts[1].substring(2))); // "B:" 제거
            dp.setLunch    (decodeMeals(parts[2].substring(2)));
            dp.setDinner   (decodeMeals(parts[3].substring(2)));
            result.add(dp);
        }
        return result;
    }

    private List<MealItemDto> decodeMeals(String encoded) {
        List<MealItemDto> list = new ArrayList<>();
        if (encoded == null || encoded.isBlank()) return list;
        for (String item : encoded.split(";")) {
            int eq = item.lastIndexOf('=');
            if (eq < 0) continue;
            String name = urlDecode(item.substring(0, eq));
            double kcal = 0;
            try { kcal = Double.parseDouble(item.substring(eq + 1)); } catch (NumberFormatException ignored) {}
            list.add(new MealItemDto(name, kcal));
        }
        return list;
    }

    /** CSV 필드 값 이스케이프 (쉼표/따옴표 포함 시 큰따옴표로 감쌈) */
    private String escapeCsv(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    private String unescapeCsv(String s) {
        if (s == null) return "";
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1).replace("\"\"", "\"");
        }
        return s;
    }

    /** 마지막 최상위 쉼표 위치 (큰따옴표 안 쉼표 제외) */
    private int findLastFieldStart(String line) {
        boolean inQ = false;
        int lastComma = -1;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') inQ = !inQ;
            else if (c == ',' && !inQ) lastComma = i;
        }
        return lastComma;
    }

    private void rewrite(List<ChallengeDto> list) {
        File f = new File(csvPath);
        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))) {
            pw.println("id,name,difficulty,duration,targetCalories,mealPlansEncoded");
            for (ChallengeDto c : list) pw.println(encode(c));
        } catch (IOException e) {
            System.err.println("[ChallengeDaoCsvImpl] 재작성 실패: " + e.getMessage());
        }
    }

    // 간단한 % 인코딩 (외부 라이브러리 없이)
    private String urlEncode(String s) {
        return s == null ? "" : s
            .replace("%", "%25").replace("=", "%3D").replace(":", "%3A")
            .replace("|", "%7C").replace("$", "%24").replace(";", "%3B");
    }

    private String urlDecode(String s) {
        return s == null ? "" : s
            .replace("%3B", ";").replace("%24", "$").replace("%7C", "|")
            .replace("%3A", ":").replace("%3D", "=").replace("%25", "%");
    }
}
