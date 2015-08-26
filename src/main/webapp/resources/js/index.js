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
    $scope.authData = {login: "", password: ""};

    $scope.singIn = function () {
        if(validateEmptyFields($scope.authData)==false) {
            var data = "login=" + encodeURIComponent($scope.authData.login) +
                "&password=" + encodeURIComponent($scope.authData.password);
            $http.post("/api/authenticate", data, {
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function (data, status, headers, config) {
                console.log("authentication succeeded ");
                $window.sessionStorage.setItem("session", $scope.authData.login);
                $window.open("#/user", "_self");
            }).error(function (data, status, headers, config) {
                console.log("authentication failed");
                show('incorrect login or password ! ');
            });
        }
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
        if(validateEmptyFields($scope.user)==false && validateRegistrationDataLength($scope.user)==true) {
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

userApp.controller('taskController', function ($scope, $http, $window) {
    $scope.task = {"taskName": "", "done": false};
    var login = $window.sessionStorage.getItem('session');

    $scope.tasks = [];

    $scope.addTask = function () {

        if($scope.task.taskName.length>0) {
            $http.post('/api/tasks/', $scope.task).
                success(function (data) {
                    $scope.tasks.push(data)
                    var inputTaskName = document.getElementById('inputTaskName');
                    inputTaskName.value = "";
                    $scope.task.taskName ="";
                }).
                error(function (data, status) {
                    if (status === 400)
                        $scope.badRequest = data;
                    else if (status === 409)
                        $scope.badRequest = 'Unkown Error !.';
                });
        }else show('Task field can not be empty !');
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
        if(validateEmptyFields($scope.userHelper)==false) {
            $http.put('/api/users', $scope.userHelper).
                success(function(response, status, headers, config){
                    $window.open("#/user/profile", "_self");
                }).
                error(function(response, status, headers, config){
                    show('Old password is incorrect !');
                });
        }
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

validateRegistrationDataLength = function (user) {
    var isValid = true;
    if(user.firstName.length>30){
        isValid = false;
        show('first name can not more longer than 30 characters');
    }
    if(user.lastName.length>50){
        isValid = false;
        show('last name can not more longer than 50 characters');
    }
    if(user.login.length>20){
        isValid = false;
        show('login can not more longer than 20 characters');
    }
    if(user.password.length>60){
        isValid = false;
        show('password can not more longer than 60 characters');
    }

    return isValid;
};

show = function(message){
    $.notify({
        message: message,
        type: 'danger'
    });
};