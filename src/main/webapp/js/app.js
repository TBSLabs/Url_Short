//Define an angular module for our app
var sampleApp = angular.module('sampleApp', []);

//Define Routing for app
//Uri /AddNewOrder -> template AddOrder.html and Controller AddOrderController
//Uri /ShowOrders -> template ShowOrders.html and Controller AddOrderController
sampleApp.config(['$routeProvider','$httpProvider',
  function($routeProvider,$httpProvider) {
    $routeProvider.
      when('/urlShort', {
	templateUrl: 'templates/urlShort.html',
	controller: 'UrlShortController'
      }).
      when('/statistics', {
	templateUrl: 'templates/statistics.html',
	controller: 'StatisticsController'
      }).
      when('/notification', {
	templateUrl: 'templates/notification.html',
	controller: 'NotificationController'
      }).
      when('/share', {
	templateUrl: 'templates/share.html',
	controller: 'ShareController'
      }).
      when('/user', {
	templateUrl: 'templates/user.html',
	controller: 'UserController'
      }).
      otherwise({
		redirectTo: '/user'
      });


      $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
      $httpProvider.defaults.headers.common["Authorization"] = 'Basic MTIwNDk2OnNpdGFrYW50';
}]);
sampleApp.controller('UrlShortController', function($scope) {	
	$scope.message = 'This is Url Shorten Page';
});
sampleApp.controller('StatisticsController', function($scope) {
	$scope.message = 'This is Url Shorten Statistics Page';
});
sampleApp.controller('NotificationController', function($scope) {
	$scope.message = 'This is Url Shorten Notification Page';
});
sampleApp.controller('ShareController', function($scope) {
	$scope.message = 'Share URL on Social Networking';
});

sampleApp.controller('UserController', function($scope,$http) {
	var responsePromise = $http.get("http://localhost:8080/api/v1/user/120496");

                responsePromise.success(function(data, status, headers, config) {
                    $scope.data=data;
                });
                responsePromise.error(function(data, status, headers, config) {
                    alert("AJAX failed!");
                });
});