const React = require('react');
const ReactDOM = require('react-dom');
const Tictacto = require('./Tictacto')
const {createRoot} = require("react-dom/client");

const root = createRoot(document.getElementById('root'));
root.render(<Tictacto/>)