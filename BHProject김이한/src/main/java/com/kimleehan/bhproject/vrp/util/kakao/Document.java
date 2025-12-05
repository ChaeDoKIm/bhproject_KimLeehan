package com.kimleehan.bhproject.vrp.util.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

	private String id;

	private Double x;
	private Double y;
	
	@JsonProperty("place_name")		// json에서 넘겨진 이름이 "place_name"으로 되어 있어서 어노테이션 없으면 placeName 변수에 안 담김
	private String placeName;

	@JsonProperty("address_name")
	private String addressName;
	private String phone;

	@JsonProperty("road_address_name")
	private String roadAddressName;

}