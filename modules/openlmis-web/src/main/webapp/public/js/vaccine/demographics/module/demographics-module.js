/*
 * This program was produced for the U.S. Agency for International Development. It was prepared by the USAID | DELIVER PROJECT, Task Order 4. It is part of a project which utilizes code originally licensed under the terms of the Mozilla Public License (MPL) v2 and therefore is licensed under MPL v2 or later.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the Mozilla Public License as published by the Mozilla Foundation, either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Mozilla Public License for more details.
 *
 * You should have received a copy of the Mozilla Public License along with this program. If not, see http://www.mozilla.org/MPL/
 */

angular.module('demographics', ['openlmis','ngTable','ui.sortable' , 'ui.bootstrap.modal', 'ui.bootstrap.dialog']).
  config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/list', {controller:DemographicEstimateCategoryController, templateUrl:'partials/category.html', resolve : DemographicEstimateCategoryController.resolve }).
        when('/category/add', {controller:DemographicEstimateCategoryFormController, templateUrl:'partials/category_form.html', resolve : DemographicEstimateCategoryFormController.resolve }).
        when('/category/edit/:id', {controller:DemographicEstimateCategoryFormController, templateUrl:'partials/category_form.html', resolve : DemographicEstimateCategoryFormController.resolve }).
        when('/facility', {controller:FacilityDemographicEstimateController, templateUrl:'partials/facility.html', resolve : FacilityDemographicEstimateController.resolve }).
        when('/district', {controller:DistrictDemographicEstimateController, templateUrl:'partials/district.html', resolve : DistrictDemographicEstimateController.resolve }).
      otherwise({redirectTo:'/list'});
  }]).run(function ($rootScope, AuthorizationService) {
  });
