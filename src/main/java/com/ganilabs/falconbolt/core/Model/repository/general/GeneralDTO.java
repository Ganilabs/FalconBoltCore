package com.ganilabs.falconbolt.core.Model.repository.general;

public class GeneralDTO {
	private Integer openedProjectId;
	
	public static GeneralDTO fromEntity(General entity) {
		GeneralDTO dto = new GeneralDTO();
		dto.openedProjectId = entity.openedProjectId;
		return dto;
	}
}
