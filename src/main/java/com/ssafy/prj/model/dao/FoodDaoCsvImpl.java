//package com.ssafy.prj.model.dao;
//
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Locale;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//import com.ssafy.prj.model.dto.FoodDto;
//
///**
// * CSV 파일(음식DB.csv)을 서버 기동 시 1회 메모리에 로드하는 구현체.
// *
// * DAO 교체 전략
// * ─────────────
// * DB 로 전환할 때는 FoodDaoDbImpl 을 새로 작성하고,
// * FoodDaoFactory (또는 FoodServiceImpl 생성자) 에서 구현체만 바꿔주면 됩니다.
// * 이 클래스 및 상위 인터페이스(FoodDao)는 그대로 유지됩니다.
// *
// * CSV 포맷 (UTF-8 BOM)
// * ─────────────────────
// * 식품코드, 식품명, 식품대분류명, 영양성분함량기준량,
// * 에너지(kcal), 단백질(g), 지방(g), 탄수화물(g),
// * 당류(g), 나트륨(mg), 포화지방산(g), 트랜스지방산(g), 식품중량
// */
//public class FoodDaoCsvImpl implements FoodDao {
//
//    // ── 싱글톤 ───────────────────────────────────────────────────────
//    private static FoodDao instance;
//
//    private FoodDaoCsvImpl() {
//        // 생성자에서는 아무것도 하지 않음.
//        // 실제 로드는 load() 에서 수행 (FoodContextListener 호출).
//    }
//
//    public static synchronized FoodDao getInstance() {
//        if (instance == null) {
//            instance = new FoodDaoCsvImpl();
//        }
//        return instance;
//    }
//
//    // ── 내부 저장소 ──────────────────────────────────────────────────
//    /** 코드 → FoodDto 인덱스 (빠른 단건 조회) */
//    private final Map<String, FoodDto> codeIndex = new HashMap<>();
//
//    /** 전체 목록 (검색용) */
//    private final List<FoodDto> allFoods = new ArrayList<>();
//
//    // ── 초기화 ───────────────────────────────────────────────────────
//
//    /**
//     * CSV 파일을 읽어 메모리에 로드합니다.
//     * FoodContextListener (ServletContextListener) 에서 서버 기동 시 1회 호출됩니다.
//     *
//     * @param csvPath 배포 환경의 실제 파일 경로 (예: servletContext.getRealPath("/WEB-INF/data/음식DB.csv"))
//     */
//    public void load(String csvPath) {
//        allFoods.clear();
//        codeIndex.clear();
//
//        File file = new File(csvPath);
//        if (!file.exists()) {
//            System.err.println("[FoodDaoCsvImpl] CSV 파일을 찾을 수 없습니다: " + csvPath);
//            return;
//        }
//
//        try (BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
//
//            String line = br.readLine(); // 헤더(BOM 포함) 스킵
//            if (line == null) return;
//
//            int count = 0;
//            while ((line = br.readLine()) != null) {
//                if (line.isBlank()) continue;
//                FoodDto dto = parseLine(line);
//                if (dto == null) continue;
//                allFoods.add(dto);
//                codeIndex.put(dto.getFoodCode(), dto);
//                count++;
//            }
//            System.out.printf("[FoodDaoCsvImpl] 음식 데이터 로드 완료: %,d 건%n", count);
//
//        } catch (IOException e) {
//            System.err.println("[FoodDaoCsvImpl] CSV 로드 실패: " + e.getMessage());
//        }
//    }
//
//    // ── FoodDao 구현 ─────────────────────────────────────────────────
//
//    @Override
//    public List<FoodDto> searchByName(String keyword, int limit) {
//        if (keyword == null || keyword.isBlank()) return Collections.emptyList();
//        String lower = keyword.toLowerCase(Locale.KOREAN);
//        return allFoods.stream()
//                .filter(f -> f.getFoodName().toLowerCase(Locale.KOREAN).contains(lower))
//                .limit(limit)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public FoodDto findByCode(String foodCode) {
//        return codeIndex.get(foodCode);
//    }
//
//    // ── 내부 헬퍼 ───────────────────────────────────────────────────
//
//    /**
//     * CSV 한 줄을 FoodDto 로 변환합니다.
//     * 따옴표로 감싼 필드(쉼표 포함 가능)를 올바르게 처리합니다.
//     */
//    private FoodDto parseLine(String line) {
//        String[] cols = splitCsv(line);
//        if (cols.length < 13) return null;
//
//        try {
//            return new FoodDto(
//                cols[0].trim(),                    // 식품코드
//                cols[1].trim(),                    // 식품명
//                cols[2].trim(),                    // 식품대분류명
//                cols[3].trim(),                    // 영양성분함량기준량
//                parseDouble(cols[4]),              // 에너지(kcal)
//                parseDouble(cols[5]),              // 단백질(g)
//                parseDouble(cols[6]),              // 지방(g)
//                parseDouble(cols[7]),              // 탄수화물(g)
//                parseDouble(cols[8]),              // 당류(g)
//                parseDouble(cols[9]),              // 나트륨(mg)
//                parseDouble(cols[10]),             // 포화지방산(g)
//                parseDouble(cols[11]),             // 트랜스지방산(g)
//                cols[12].trim()                    // 식품중량 (문자열 유지)
//            );
//        } catch (Exception e) {
//            return null; // 파싱 오류 행은 조용히 무시
//        }
//    }
//
//    /** 숫자 앞뒤 단위 문자(g, kcal, mg 등)를 제거하고 double 로 변환 */
//    private double parseDouble(String raw) {
//        if (raw == null || raw.isBlank()) return 0.0;
//        String cleaned = raw.trim().replaceAll("[^0-9.]", "");
//        if (cleaned.isEmpty()) return 0.0;
//        try {
//            return Double.parseDouble(cleaned);
//        } catch (NumberFormatException e) {
//            return 0.0;
//        }
//    }
//
//    /**
//     * RFC 4180 준수 CSV 분할 (따옴표 내부 쉼표 처리).
//     */
//    private String[] splitCsv(String line) {
//        List<String> result = new ArrayList<>();
//        StringBuilder cur = new StringBuilder();
//        boolean inQuotes = false;
//
//        for (int i = 0; i < line.length(); i++) {
//            char c = line.charAt(i);
//            if (c == '"') {
//                // 이중 따옴표 ""는 하나의 " 로 처리
//                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
//                    cur.append('"');
//                    i++;
//                } else {
//                    inQuotes = !inQuotes;
//                }
//            } else if (c == ',' && !inQuotes) {
//                result.add(cur.toString());
//                cur.setLength(0);
//            } else {
//                cur.append(c);
//            }
//        }
//        result.add(cur.toString());
//        return result.toArray(new String[0]);
//    }
//}
