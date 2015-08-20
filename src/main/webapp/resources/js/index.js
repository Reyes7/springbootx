var userApp = angular.module('userApp', ['ngRoute']);

userApp.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: 'resources/pages/login.html',
            controller: 'loginController'
        })
        .when('/register', {
            templateUrl: 'resources/pages/register.html',
            controller: 'registerController'
        })
        .when('/user', {
            templateUrl: 'resources/pages/user_panel.html',
            controller: 'panelController'
        })
        .when('/user/update', {
            templateUrl: 'resources/pages/update_user.html',
            controller: 'profileController'
        })
        .when('/user/profile', {
            templateUrl: 'resources/pages/profile.html',
            controller: 'profileController'
        });
});

userApp.controller('loginController', function ($scope, $http, $window) {
    $scope.singIn = function () {
        var data = "login=" + encodeURIComponent($scope.login) +
            "&password=" + encodeURIComponent($scope.password);
        $http.post("/api/authenticate", data, {
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).success(function (data, status, headers, config) {
            console.log("authentication succeeded ");
            $window.sessionStorage.setItem("session", $scope.login);
            $window.open("#/user", "_self");
        }).error(function (data, status, headers, config) {
            console.log("authentication failed")
        });
    };
});

userApp.controller('panelController', function ($scope, $http, $window) {
    $scope.login = $window.sessionStorage.getItem('session');

    $scope.openUserProfile = function () {
        $window.open("#user/profile", "_self");
    };

    $scope.logout = function () {
        $http.get('/logout').success(function (data) {
            $window.sessionStorage.clear();
            $window.open("#/", "_self");
        });
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

userApp.controller('taskController', function ($scope, $http, $window, $route) {
    $scope.task = {"taskName": "", "done": false};
    var login = $window.sessionStorage.getItem('session');

    $scope.tasks = [];

    $scope.addTask = function () {
        $http.post('/api/tasks/' + login, $scope.task).
            success(function (data) {
                $scope.tasks.push(data)
            }).
            error(function (data, status) {
                if (status === 400)
                    $scope.badRequest = data;
                else if (status === 409)
                    $scope.badRequest = 'Unkown Error !.';
            });
    };

    $scope.updateTask = function () {
        var selects = $('#table').bootstrapTable('getSelections');
        ids = $.map(selects, function (row) {
            return row.id;
        });

        for (var i = 0; i < ids.length; i++) {
            console.log("Update task with id: ", ids[i]);
            $http.get('/api/tasks/' + ids[i]).
                success(function (data) {
                    console.log("Updated task: ", data.toString());
                }).
                error(function () {
                    console.log('failed to update task');
                });
        }
    };

    $scope.deleteTask = function () {
        var selects = $('#table').bootstrapTable('getSelections');
        ids = $.map(selects, function (row) {
            return row.id;
        });

        for (var i = 0; i < ids.length; i++) {
            $http.delete('/api/tasks/' + ids[i]);
        }
    };

    $scope.getTasks = function () {
        $http.get('api/tasks').
            success(function (data) {
                $scope.tasks.length = 0;
                Array.prototype.push.apply($scope.tasks, data);
                console.log($scope.tasks);
            }).
            error(function () {
                console.log('failed to get tasks');
            });
    };

    $scope.getTasks();
});

userApp.controller('profileController', function ($scope, $http, $window, $route) {
    $scope.userHelper = {
        login: $window.sessionStorage.getItem('session'),
        firstName: "", lastName: "", oldPassword: "", newPassword: ""
    };

    $scope.getUser = function () {
        $http.get('/api/user/' + $window.sessionStorage.getItem('session')).
            success(function (data) {
                $scope.user = data;
            });
    };

    $scope.updateUser = function () {
        $http.put('/api/users', $scope.userHelper);
        $window.open("#/user/profile", "_self");
    };

    $scope.deleteUser = function () {
        $http.delete('/api/user/' + $window.sessionStorage.getItem('session'));
        $window.sessionStorage.clear();
        $window.open("#/", "_self");
    };

    $scope.openUserEditProfile = function () {
        $window.open("#user/update", "_self");
    };
});
