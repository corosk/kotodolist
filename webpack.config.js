var path = require('path')
var webpack = require('webpack')
var fs = require('fs')


const vendor = { vendor: ['vue'] }
const entries = fs.readdirSync('./src/main/js')
                    .filter(a => /\.js$/.test(a))
                    .reduce((acc, x) => {
                        acc[x.slice(0, -3)] = './src/main/js/' + x
                        return acc
                    }, vendor)

module.exports = {
    entry: entries,
    output: {
        path: path.resolve(__dirname, './src/main/resources/static/js'),
        publicPath: '/js/',
        filename: '[name].js',
    },
    plugins: [
        new webpack.ProvidePlugin({
            $: 'jquery',
            jQuery: 'jquery'
        })
    ],
    module : {
        rules: [
            {
                test: /\.css/,
                use: [
                    'style-loader',
                    {
                        loader: 'css-loader',
                        options: {
                            url: false,
                            sourceMap: true,
                        },
                    },
                ],
            },
        ]
    },
    resolve: {
        alias: {
            'vue$': 'vue/dist/vue.esm.js'
        }
    },
    devServer: {
        historyApiFallback: true,
        noInfo: true,
        contentBase: path.join(__dirname, "./bin/static"),
    },
    performance: {
        hints: false
    },
    devtool: '#eval-source-map'
}