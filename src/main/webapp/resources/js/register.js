var register_app = angular.module('register', []);

register_app.controller('registerController', function($scope, $http) {
    $scope.user = {firstName:"", lastName:""};

    $scope.submit = function() {
    	$scope.submitting = true;
    	$http({
    	    method: 'POST',
    	    url: '/users',
    	    data: $scope.user
    	}).success(function(data) {
    	    $scope.submitting = false;
    	}).error(function(data, status) {
    	    $scope.submitting = false;
    	    if (status === 400)
    		$scope.badRequest = data;
    	    else if (status === 409)
    		$scope.badRequest = 'The name is already used.';
    	});
    };
});