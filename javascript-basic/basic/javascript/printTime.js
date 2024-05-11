const map = new Map([['key1', 'key2'], ['key3', 'key4']]);
const {size} = map;

map.set('newKey', 'newValue');

for (const key of map.keys()) {
    console.log(key);
}