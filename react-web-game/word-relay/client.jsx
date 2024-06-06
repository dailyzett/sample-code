const React = require('react');
const ReactDOM = require('react-dom');
const WorldRelay = require('./WorldRelay')
const {createRoot} = require("react-dom/client");

const root = createRoot(document.getElementById('root'));
root.render(<WorldRelay/>)