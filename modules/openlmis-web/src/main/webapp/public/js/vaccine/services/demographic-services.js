/*
 * This program was produced for the U.S. Agency for International Development. It was prepared by the USAID | DELIVER PROJECT, Task Order 4. It is part of a project which utilizes code originally licensed under the terms of the Mozilla Public License (MPL) v2 and therefore is licensed under MPL v2 or later.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Mozilla Public License as published by the Mozilla Foundation, either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Mozilla Public License for more details.
 *
 * You should have received a copy of the Mozilla Public License along with this program. If not, see http://www.mozilla.org/MPL/
 */

services.factory('DemographicEstimateCategories', function ($resource) {
  return $resource('/vaccine/demographic/estimate/categories.json', {}, {});
});

services.factory('DemographicEstimateCategory', function ($resource) {
  return $resource('/vaccine/demographic/estimate/category/:id.json', {}, {});
});

services.factory('SaveDemographicEstimateCategory', function ($resource) {
  return $resource('/vaccine/demographic/estimate/category/save.json', {}, update);
});

services.factory('FacilityDemographicEstimates', function ($resource) {
  return $resource('/vaccine/demographic/estimate/facility/get.json', {}, {});
});

services.factory('SaveFacilityDemographicEstimates', function ($resource) {
  return $resource('/vaccine/demographic/estimate/facility/save.json', {}, update);
});

services.factory('DistrictDemographicEstimates', function ($resource) {
  return $resource('/vaccine/demographic/estimate/district/get.json', {}, {});
});

services.factory('SaveDistrictDemographicEstimates', function ($resource) {
  return $resource('/vaccine/demographic/estimate/district/save.json', {}, update);
});
