var app = angular.module('profileApp', []);
app.controller('profileController', [ '$scope', '$sce', '$http', function($scope, $sce, $http){
    $scope.user = {
        name: "Matan Sokolovsky",
        pic: "images/profile.jpg",
        queries_submitted: 3,
        questions_answered: 36
    };
}]);