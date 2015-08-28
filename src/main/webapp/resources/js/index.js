var userApp = angular.module('userApp', ['pascalprecht.translate','ngRoute']);

userApp.config(function ($routeProvider,$translateProvider) {
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

    $translateProvider.translations('en', {
        //login page
        CHOOSE_LANG:                'Choose language',
        BUTTON_LANG_EN:             'English',
        BUTTON_LANG_DE:             'German',
        BUTTON_LANG_PL:             'Polish',
        LOGIN_TITLE:                'Please sign in',
        LOGIN_FIELD:                'Login',
        PASSWORD_FIELD:             'Password',
        LOGIN_BUTTON:               'Sign in',
        REGISTER_BUTTON:            'Register',

        //register page
        REGISTER_TITLE:             'User Registration',
        FIRST_NAME:                 'First Name',
        LAST_NAME:                  'Last Name',
        LOGIN:                      'Login',
        PASSWORD:                   'Password',
        FIRST_NAME_FIELD:           'Enter First Name',
        LAST_NAME_FIELD:            'Enter Last Name',
        REGISTER_LOGIN_FIELD:       'Enter Login',
        REGISTER_PASSWORD_FIELD:    'Enter Password',
        DONE_BUTTON:                'Done',
        BACK_BUTTON:                'Back to login',

        //user panel
        USER_TITLE:                 'Hello',
        PROFILE_BUTTON:             'Profile',
        LOGOUT_BUTTON:              'Logout',
        TASK_TITLE:                 'Task',
        TASK_FIELD:                 'Enter the name of your task',
        TASK_LIST_HEADER:           'Your tasks',
        CHANGE_TASK:                'change',
        TASK_NAME:                  'task',
        TASK_DELETE:                'delete'
    });
    $translateProvider.translations('de', {
        //login page
        CHOOSE_LANG:                'Wähle sprache',
        BUTTON_LANG_EN:             'Englisch',
        BUTTON_LANG_DE:             'Deutsch',
        BUTTON_LANG_PL:             'Polnisch',
        LOGIN_TITLE:                'Bitte loggen sich',
        LOGIN_FIELD:                'Login',
        PASSWORD_FIELD:             'Kennwort',
        LOGIN_BUTTON:               'Melden Sie sich an',
        REGISTER_BUTTON:            'Registrieren',

        //register page
        REGISTER_TITLE:             'Benutzer Anmelden',
        FIRST_NAME:                 'Vorname',
        LAST_NAME:                  'Zuname',
        LOGIN:                      'Login',
        PASSWORD:                   'Kennwort',
        FIRST_NAME_FIELD:           'Geben Sie Vornamen',
        LAST_NAME_FIELD:            'Geben Sie Nachnamen',
        REGISTER_LOGIN_FIELD:       'Geben Sie Login',
        REGISTER_PASSWORD_FIELD:    'Geben Sie Kennwort',
        DONE_BUTTON:                'Fertig',
        BACK_BUTTON:                'Zurück zum Login',

        //user panel
        USER_TITLE:                 'Willkommen',
        PROFILE_BUTTON:             'Profil',
        LOGOUT_BUTTON:              'Ausloggen',
        TASK_TITLE:                 'Aufgabe',
        TASK_FIELD:                 'Geben Sie den Namen Ihrer Aufgabe',
        TASK_LIST_HEADER:           'Ihre Aufgaben',
        CHANGE_TASK:                'anderung',
        TASK_NAME:                  'aufgabe',
        TASK_DELETE:                'entfernen'
    });
    $translateProvider.translations('pl', {
        //login page
        CHOOSE_LANG:                'Wybierz język',
        BUTTON_LANG_EN:             'Angielski',
        BUTTON_LANG_DE:             'Niemiecki',
        BUTTON_LANG_PL:             'Polski',
        LOGIN_TITLE:                'Zaloguj się',
        LOGIN_FIELD:                'Login',
        PASSWORD_FIELD:             'Hasło',
        LOGIN_BUTTON:               'Zaloguj',
        REGISTER_BUTTON:            'Zarejestruj',

        //register page
        REGISTER_TITLE:             'Rejestracja Użytkownika',
        FIRST_NAME:                 'Imię',
        LAST_NAME:                  'Nazwisko',
        LOGIN:                      'Login',
        PASSWORD:                   'Hasło',
        FIRST_NAME_FIELD:           'Podaj Imię',
        LAST_NAME_FIELD:            'Podaj Nazwisko',
        REGISTER_LOGIN_FIELD:       'Podaj Login',
        REGISTER_PASSWORD_FIELD:    'Podaj Hasło',
        DONE_BUTTON:                'Zrobione',
        BACK_BUTTON:                'Powrót do strony logowania',

        //user panel
        USER_TITLE:                 'Witaj',
        PROFILE_BUTTON:             'Profil',
        LOGOUT_BUTTON:              'Wyloguj',
        TASK_TITLE:                 'Zadanie',
        TASK_FIELD:                 'Wprowadź nazwę twojego zadania',
        TASK_LIST_HEADER:           'Zadania',
        CHANGE_TASK:                'zmień',
        TASK_NAME:                  'zadanie',
        TASK_DELETE:                'usuń'
    });

    $translateProvider.preferredLanguage('en');
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

userApp.controller('profileController', function ($scope, $http, $window) {
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
        show('first name can not more than 30 characters');
    }
    if(user.lastName.length>50){
        isValid = false;
        show('last name can not more than 50 characters');
    }
    if(user.login.length>20){
        isValid = false;
        show('login can not more than 20 characters');
    }
    if(user.password.length>60){
        isValid = false;
        show('password can not more than 60 characters');
    }

    return isValid;
};

show = function(message){
    $.notify({
        message: message,
        type: 'danger'
    });
};

userApp.controller('langController', function ($scope, $translate) {
    $scope.changeLanguage = function (key) {
        $translate.use(key);
    };
});