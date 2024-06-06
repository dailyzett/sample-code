const path = require('path')
const webpack = require('webpack')

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
            options: {
                presets: [
                    ['@babel/preset-env', {
                        targets: {browsers: ['> 1% in KR']},
                        debug: true,
                    }],
                    '@babel/preset-react'],
            },
        }],
    },
    plugins: [
        new webpack.LoaderOptionsPlugin({debug: true})
    ],
    output: {
        path: path.join(__dirname, '/dist'),
        filename: 'app.js',
    }, //출력
};