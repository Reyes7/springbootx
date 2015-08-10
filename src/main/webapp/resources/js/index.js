var userApp = angular.module('userApp', ['ngRoute']);

userApp.config(function($routeProvider) {
	$routeProvider
		.when('/', {
			templateUrl : 'resources/pages/login.html',
			controller  : 'loginController'
		})
		.when('/register', {
        	templateUrl : 'resources/pages/register.html',
        	controller  : 'registerController'
        })
		.when('/user', {
        	templateUrl : 'resources/pages/user_panel.html',
        	controller  : 'panelController'
        })
		.when('/user/update', {
			templateUrl : 'resources/pages/update_user.html',
			controller	: 'profileController'
		})
		.when('/user/profile', {
			templateUrl : 'resources/pages/profile.html',
			controller  : 'profileController'
		});
});

userApp.controller('panelController', function($scope, $window) {
	$scope.login = $window.sessionStorage.getItem( 'login' )

	$scope.openUserProfile = function(){
		$window.open("#user/profile","_self");
	}

	$scope.logout = function() {
		$window.sessionStorage.clear();
		$window.open("#/","_self");
	}
});

userApp.controller('registerController', function($scope, $http, $window) {
    $scope.user = {firstName:"", lastName:"", login:"", password:""};

    $scope.submit = function() {
    	$scope.submitting = true;
    	$http({
    	    method: 'POST',
    	    url: '/users',
    	    data: $scope.user
    	}).success(function(data) {
    	    $scope.submitting = false;
    	    $window.open("#/","_self");
    	}).error(function(data, status) {
    	    $scope.submitting = false;
    	    if (status === 400)
    		$scope.badRequest = data;
    	    else if (status === 409)
    		$scope.badRequest = 'The name is already used.';
    	});
    };
});

userApp.controller('loginController', function($scope, $http,$window) {

	$scope.singIn = function() {
		$scope.submitting = true;
		$http({
			method: 'POST',
			url: '/loggin',
			headers: {'Content-Type': 'application/x-www-form-urlencoded'},
			transformRequest: function(obj) {
				var str = [];
				for(var p in obj)
					str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
				return str.join("&");
			},
			data: {login: $scope.login, password: $scope.password}
		}).success(function(data) {
			$scope.submitting = false;
			$window.sessionStorage.setItem('login', data.login);
			$window.open("#/user","_self");
		}).error(function(data, status) {
			$scope.submitting = false;
			if (status === 400)
				$scope.badRequest = data;
		});
	};
});

userApp.controller('taskController', function($scope, $http,$window,$route) {
	$scope.task = {"taskName" : "", "done" : false};
	var login = $window.sessionStorage.getItem( 'login' );

	$scope.addTask = function() {
		$scope.submitting = true;
		$http({
			method: 'POST',
			url: '/tasks/'+login,
			data: $scope.task
		}).success(function(data) {
			$scope.submitting = false;
			$route.reload();
		}).error(function(data, status) {
			$scope.submitting = false;
			if (status === 400)
				$scope.badRequest = data;
			else if (status === 409)
				$scope.badRequest = 'Unkown Error !.';
		});
	};

	$scope.updateTask = function() {
		var selects = $('#table').bootstrapTable('getSelections');
		ids = $.map(selects, function (row) {
			return row.id;
		});

		for(var i = 0; i < ids.length; i++) {
			$http.get('/tasks/' + ids[i]);
		}

		$route.reload();
	}

	$scope.deleteTask = function() {
		var selects = $('#table').bootstrapTable('getSelections');
		ids = $.map(selects, function (row) {
			return row.id;
		});

		for(var i = 0; i < ids.length; i++) {
			$http.delete('/tasks/' + ids[i]);
		}

		$route.reload();
	};

	$('#table').bootstrapTable({
		url: '/tasks/'+login
	});
});

userApp.controller('profileController', function ($scope, $http,$window) {
	$scope.userHelper = {login:$window.sessionStorage.getItem( 'login' ),
		firstName:"", lastName:"", oldPassword:"", newPassword:""};

	$scope.getUser = function () {
		$http.get('/user/'+ $window.sessionStorage.getItem( 'login' )).
				success(function (data) {
					$scope.user = data;
				});
	}

	$scope.updateUser = function () {
		$http.put('/users',$scope.userHelper);
		$window.open("#/user/profile","_self");
	}

	$scope.deleteUser = function(){
		$http.delete('/user/'+ $window.sessionStorage.getItem( 'login' ));
		$window.sessionStorage.clear();
		$window.open("#/","_self");
	}

	$scope.openUserEditProfile = function(){
		$window.open("#user/update","_self");
	}
});