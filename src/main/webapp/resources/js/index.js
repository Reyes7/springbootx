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

    show = function(message){
        $.notify({
            message: message,
            type: 'danger'
        });
    };

    validateEmptyFields = function (user) {
        var isEmptyField = false;
        for (var i in user) {
            if (user.hasOwnProperty(i)) {
                if (user[i].length == 0) {
                    isEmptyField = true;
                    show(i + ' field can not be empty');
                }
            }
        }
        return isEmptyField;
    };

    $scope.submit = function () {
        if(validateEmptyFields($scope.user)==false) {
            $http.post('/api/register', $scope.user).
                success(function () {
                    $window.open("#/", "_self");
                }).
                error(function (data, status) {
                    show('User with login: ' + $scope.user.login + ' already exists');
                    console.log("failed to register as ", $scope.user.login);
                });
        }
    };
});

userApp.controller('taskController', function ($scope, $http, $window, $route) {
    $scope.task = {"taskName": "", "done": false};
    var login = $window.sessionStorage.getItem('session');

    $scope.tasks = [];

    $scope.addTask = function () {
        $http.post('/api/tasks/', $scope.task).
            success(function (data) {
                $scope.tasks.push(data)
                var inputTaskName = document.getElementById('inputTaskName');
                inputTaskName.value = "";
            }).
            error(function (data, status) {
                if (status === 400)
                    $scope.badRequest = data;
                else if (status === 409)
                    $scope.badRequest = 'Unkown Error !.';
            });
    };

    $scope.updateTask = function (id) {
        var i;

        for (i = 0; i < $scope.tasks.length; i++) {
            if (id == $scope.tasks[i].id) {
                break;
            }
        }

        $http.put('/api/tasks/' + id,
            {
                "taskName": $scope.tasks[i].taskName,
                "done": $scope.tasks[i].done
            }).
            success(function () {
                console.log('updated task with id: ', id);
            });
    };

    $scope.deleteTask = function (id) {
        var i;
        for (i = 0; i < $scope.tasks.length; i++) {
            if (id == $scope.tasks[i].id) {
                break;
            }
        }

        $http.delete('/api/tasks/' + id).
            success(function () {
                $scope.tasks.splice(i, 1);
                console.log('deleted task with id: ', id);
            });
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
        $http.get('/api/users/user').
            success(function (data) {
                $scope.user = data;
            });
    };

    $scope.updateUser = function () {
        $http.put('/api/users', $scope.userHelper);
        $window.open("#/user/profile", "_self");
    };

    $scope.deleteUser = function () {
        $http.delete('/api/users/');
        $window.sessionStorage.clear();
        $window.open("#/", "_self");
    };

    $scope.openUserEditProfile = function () {
        $window.open("#user/update", "_self");
    };
});
