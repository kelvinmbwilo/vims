/*
 * This program was produced for the U.S. Agency for International Development. It was prepared by the USAID | DELIVER PROJECT, Task Order 4. It is part of a project which utilizes code originally licensed under the terms of the Mozilla Public License (MPL) v2 and therefore is licensed under MPL v2 or later.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Mozilla Public License as published by the Mozilla Foundation, either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Mozilla Public License for more details.
 *
 * You should have received a copy of the Mozilla Public License along with this program. If not, see http://www.mozilla.org/MPL/
 */

package org.openlmis.report.mapper.lookup;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.openlmis.report.model.dto.FacilityType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityTypeReportMapper {

    // show only facility types that are likely to report rnr
  @Select("SELECT id, name ," +
          " code , nominalMaxmonth , nominalEOP , active , displayOrder"+
          "   FROM " +
          "       facility_types where id in " +
          " (select typeid from facilities where facilities.id in " +
          "     (select facilityid from programs_supported)" +
          " ) " +
          " order by name")
  List<FacilityType> getAll();

    @Select("select * from facility_types")
  List<FacilityType> getAllFacilityTypes();


  // show only facility types that are likely to report rnr
  @Select("SELECT id, name " +
          "   FROM " +
          "       facility_types where id in " +
          " (select typeid from facilities where facilities.id in " +
          "     (select facilityid from programs_supported where programid = #{programId} and active = true)" +
          " ) " +
          " order by name")
  List<FacilityType> getForProgram(@Param("programId") Long programId);

  @Select("SELECT DISTINCT facility_types.code" +
          ", facility_types.name" +
          ", facility_types.displayorder" +
          " FROM programs_supported" +
          "   JOIN facilities ON facilities.id = programs_supported.facilityid" +
          "   JOIN facility_types ON facility_types.id = facilities.typeid" +
          " WHERE programs_supported.programid = #{programId}" +
          "   AND programs_supported.active = TRUE" +
          "   AND facilities.id = ANY (#{facilityIds}::INT[])")
  List<FacilityType> getLevels(@Param("programId") Long programId, @Param("facilityIds")String facilityIds);

  @Select("SELECT id, name " +
      "   FROM " +
      "       facility_types where id = #{id}")
  FacilityType getById(Long id);

}
