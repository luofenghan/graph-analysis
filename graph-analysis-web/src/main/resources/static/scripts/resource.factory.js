/**
 * Created by cwc on 2017/4/14 0014.
 */
angular.module('graphAnalysisApp')
    .factory('Account', function ($resource, $http, $httpParamSerializer) {
        var resource = $resource('/api/account/:id', {}, {
            "getCurrentAccount": {method: 'GET', params: {}, isArray: false}
        });

        var changePassword = function (currentPassword, newPassword) {
            return $http({
                method: "POST",
                url: "/api/account/change-password",
                data: $httpParamSerializer({
                    'currentPassword': currentPassword,
                    'newPassword': newPassword
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            });
        };

        var assignRoles = function (userId, roles) {
            return $http({
                method: "POST",
                url: "/api/account/assign-role",
                data: $httpParamSerializer({
                    'userId': userId,
                    'roles': roles.join(',')
                }),
                headers: {'Content-Type': 'application/x-www-form-urlencoded'}
            });
        };

        return {
            "createAccount": resource.save,
            "getCurrentAccount": resource.getCurrentAccount
        }
    });