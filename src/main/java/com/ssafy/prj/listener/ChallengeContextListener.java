package com.ssafy.prj.listener;



import com.ssafy.prj.model.dao.ChallengeDaoFileImpl;
import com.ssafy.prj.model.dao.FoodDaoCsvImpl;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * 서버(웹 컨텍스트) 시작 시 CSV 데이터를 메모리에 로드합니다.
 *
 * 파일 배치 위치 (WEB-INF/data/ 아래):
 *   - 음식DB.csv         → FoodDaoCsvImpl
 *   - challenges.csv    → ChallengeDaoCsvImpl (없으면 자동 생성)
 */
@WebListener
public class ChallengeContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	    ServletContext ctx = sce.getServletContext();

	    String foodCsvPath      = ctx.getRealPath("/WEB-INF/data/음식DB.csv");
	    String challengeCsvPath = ctx.getRealPath("/WEB-INF/data/challenges.csv");

	    System.out.println("[Listener] food    = " + foodCsvPath);
	    System.out.println("[Listener] challenge = " + challengeCsvPath);

	    FoodDaoCsvImpl foodDao = (FoodDaoCsvImpl) FoodDaoCsvImpl.getInstance();
	    foodDao.load(foodCsvPath);

	    ChallengeDaoFileImpl challengeDao = (ChallengeDaoFileImpl) ChallengeDaoFileImpl.getInstance();
	    challengeDao.init(challengeCsvPath);
	}

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[ChallengeContextListener] 서버 종료");
    }
}
