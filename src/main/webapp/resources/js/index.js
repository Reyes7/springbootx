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

userApp.controller('loginController', function($scope, $http,$window) {
	$scope.singIn = function() {
		var data = "login=" + encodeURIComponent($scope.login) +
				"&password=" + encodeURIComponent($scope.password);
		$http.post("/api/authenticate", data, {
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).success(function(data, status, headers, config){
			console.log("authentication succeeded ")
			sessionStorage.setItem("session",data);
		}).error(function(data, status, headers, config){
			console.log("authentication failed")
		});
	};
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

userApp.controller('registerController', function ($scope, $http, $window) {
	$scope.user = {firstName: "", lastName: "", login: "", password: ""};

	$scope.submit = function () {
		$scope.submitting = true;
		$http.post('/api/register', $scope.user).
				success(function () {
					$scope.submitting = false;
					$window.open("#/", "_self");
				}).
				error(function (data, status) {
					$scope.submitting = false;
					console.log("failed to register as ", $scope.user.login);
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
			url: '/api/tasks/'+login,
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
			$http.get('/api/tasks/' + ids[i]);
		}

		$route.reload();
	}

	$scope.deleteTask = function() {
		var selects = $('#table').bootstrapTable('getSelections');
		ids = $.map(selects, function (row) {
			return row.id;
		});

		for(var i = 0; i < ids.length; i++) {
			$http.delete('/api/tasks/' + ids[i]);
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
		$http.get('/api/user/'+ $window.sessionStorage.getItem( 'login' )).
				success(function (data) {
					$scope.user = data;
				});
	}

	$scope.updateUser = function () {
		$http.put('/api/users',$scope.userHelper);
		$window.open("#/user/profile","_self");
	}

	$scope.deleteUser = function(){
		$http.delete('/api/user/'+ $window.sessionStorage.getItem( 'login' ));
		$window.sessionStorage.clear();
		$window.open("#/","_self");
	}

	$scope.openUserEditProfile = function(){
		$window.open("#user/update","_self");
	}
});
