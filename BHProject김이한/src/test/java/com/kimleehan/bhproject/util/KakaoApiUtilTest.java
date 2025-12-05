package com.kimleehan.bhproject.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kimleehan.bhproject.vrp.util.KakaoApiUtil;
import com.kimleehan.bhproject.vrp.util.kakao.KakaoDirections;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KakaoApiUtilTest {

	@Test
	public void getPointByKeywordTest() throws IOException, InterruptedException {
		KakaoApiUtil.Point center = new KakaoApiUtil.Point(126.675113024566, 37.4388938204128);//인천일보아카데미
		List<KakaoApiUtil.Point> pointByKeyword = KakaoApiUtil.getPointByKeyword("약국", center);
		for (KakaoApiUtil.Point point : pointByKeyword) {
			System.out.println(//
					"x:" + point.getX() //
							+ ",y:" + point.getY()//
							+ ",name:" + point.getName()//
							+ ",phone:" + point.getPhone()//
			);
		}
	}

	@Test
	public void getPointByAddressTest() throws IOException, InterruptedException {
		KakaoApiUtil.Point point = KakaoApiUtil.getPointByAddress("인천광역시 미추홀구 매소홀로488번길 6-32 태승빌딩 5층");
		System.out.println("x:" + point.getX() + ",y:" + point.getY());
	}

	@Test
	public void getVehiclePathsTest() throws IOException, InterruptedException {
		KakaoApiUtil.Point from = KakaoApiUtil.getPointByAddress("인천광역시 미추홀구 매소홀로488번길 6-32 태승빌딩 5층");
		System.out.println("인천일보아카데미) x:" + from.getX() + ",y:" + from.getY());
		KakaoApiUtil.Point to = KakaoApiUtil.getPointByAddress("와우산로 23길 20 패스트파이브 5층");
		System.out.println("브이유에스) x:" + to.getX() + ",y:" + to.getY());

		System.out.println("출발!!");
		List<KakaoApiUtil.Point> vehiclePaths = KakaoApiUtil.getVehiclePaths(from, to);
		for (KakaoApiUtil.Point point : vehiclePaths) {
			System.out.println("x:" + point.getX() + ",y:" + point.getY());
		}
	}

	@Test
	public void getPointByAddressListTest() throws IOException, InterruptedException {
		List<String> addresses = new ArrayList<>();
		addresses.add("인천 미추홀구 소성로 333");
		addresses.add("인천 미추홀구 매소홀로 578");
		addresses.add("인천 미추홀구 한나루로 411");
		addresses.add("인천 미추홀구 소성로 150");
		List<KakaoApiUtil.Point> pointByAddressList = KakaoApiUtil.getPointListByAddress(addresses);

		for (KakaoApiUtil.Point point : pointByAddressList) {
			System.out.println(//
					"x:" + point.getX() //
							+ ",y:" + point.getY()//
							+ ",name:" + point.getName()//
							+ ",phone:" + point.getPhone()//
							+ ",id:" + point.getId()//
			);
		}
	}

	@Test
	public void getKakaoDirectionsTest() throws IOException, InterruptedException {
		KakaoApiUtil.Point from = new KakaoApiUtil.Point(126.675113024566, 37.4388938204128);// 인천일보아카데미
		System.out.println("인천일보아카데미) x:" + from.getX() + ",y:" + from.getY());
		KakaoApiUtil.Point to = KakaoApiUtil.getPointByAddress("와우산로 23길 20 패스트파이브 5층");
		System.out.println("브이유에스) x:" + to.getX() + ",y:" + to.getY());
		System.out.println("출발!!");
		KakaoDirections kakaoDirections = KakaoApiUtil.getKakaoDirections(from, to);
		List<KakaoDirections.Route> routes = kakaoDirections.getRoutes();
		KakaoDirections.Route route = routes.get(0);
		List<KakaoApiUtil.Point> pointList = new ArrayList<KakaoApiUtil.Point>();
		List<KakaoDirections.Route.Section.Road> roads = route.getSections().get(0).getRoads();
		for (KakaoDirections.Route.Section.Road road : roads) {
			List<Double> vertexes = road.getVertexes();
			for (int i = 0; i < vertexes.size(); i++) {
				pointList.add(new KakaoApiUtil.Point(vertexes.get(i), vertexes.get(++i)));
			}
		}
		KakaoDirections.Route.Summary summary = route.getSummary();
		Integer distance = summary.getDistance();
		Integer duration = summary.getDuration();
		KakaoDirections.Route.Summary.Fare fare = summary.getFare();
		Integer taxi = fare.getTaxi();
		Integer toll = fare.getToll();
		System.out.println("이동거리:" + distance);
		System.out.println("이동시간:" + duration);
		System.out.println("택시비:" + taxi);
		System.out.println("톨비:" + toll);
		System.out.println("경로:" + new ObjectMapper().writeValueAsString(pointList));
	}


}