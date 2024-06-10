const path = require('path')
const webpack = require('webpack')
const ReactRefreshWebpackPlugin = require("@pmmmwh/react-refresh-webpack-plugin");

module.exports = {
    name: 'world-relay-setting',
    mode: 'development',
    devtool: 'eval',
    resolve: {
        extensions: ['.js', '.jsx'],
    },
    entry: {
        app: ['./client'],
    }, //입력

    module: {
        rules: [{
            test: /\.jsx?/,
            loader: 'babel-loader',
            exclude: path.join(__dirname, "node_modules"),
            options: {
                presets: [
                    [
                        "@babel/preset-env",
                        {
                            targets: {browsers: ['> 5% in KR']},
                            debug: true,
                        },
                    ],
                    "@babel/preset-react",
                ],
                plugins: ["react-refresh/babel"],
            },
        }],
    },
    plugins: [new ReactRefreshWebpackPlugin()],
    output: {
        path: path.join(__dirname, '/dist/'),
        filename: 'app.js',
        publicPath: '/dist/'
    }, //출력

    devServer: {
        devMiddleware: {publicPath: "/dist/"},
        static: {directory: path.resolve(__dirname)},
        hot: true,
    },
};