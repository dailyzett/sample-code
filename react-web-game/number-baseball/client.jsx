const React = require('react');
const ReactDOM = require('react-dom');
const NumberBaseball = require('./NumberBaseball')
const {createRoot} = require("react-dom/client");

const root = createRoot(document.getElementById('root'));
root.render(<NumberBaseball/>)