var userApp = angular.module('userApp');

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
