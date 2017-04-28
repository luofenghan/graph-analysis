var graphAnalysisApp = angular.module("graphAnalysisApp", ["ui.router", "oc.lazyLoad", "ngResource"]);

graphAnalysisApp.config(['$ocLazyLoadProvider', function ($ocLazyLoadProvider) {

    $ocLazyLoadProvider.config({});

}]).config(['$locationProvider', function ($locationProvider) {

    $locationProvider.html5Mode(false);

}]).config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise("/home");

    $stateProvider
        .state("home", {
            url: "/home",
            templateUrl: "/scripts/app/home/home.html",
            page: {title: '首页'},
            controller: "HomeController",
            resolve: {
                deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load('/scripts/app/home/home.controller.js');
                }]
            }
        })
        .state("data-source", {
            url: "/data-source",
            templateUrl: "/scripts/app/data-source/data-source.html",
            page: {title: "数据源管理"},
            controller: "DataSourceController",
            resolve: {
                deps: ['$ocLazyLoad', function ($ocLazyLoad) {
                    return $ocLazyLoad.load([
                        {
                            insertBefore: '#lazy-load-js-before-footer',
                            files: ['/scripts/app/data-source/data-source.controller.js']
                        },
                        {
                            insertBefore: "#lazy-load-css-before-favicon",
                            files: ['/assets/styles/data-source.css']
                        }
                    ]);

                }]
            }
        });
}]).controller('AppController', ['$scope', '$rootScope', 'Account', function ($scope, $rootScope, Account) {

    $scope.currentUser = Account.getCurrentAccount();
}]).run(["$rootScope", "$state", function ($rootScope, $state) {
    $rootScope.$state = $state; // state to be accessed from view
    // $rootScope.$settings = settings; // state to be accessed from view
}]);
