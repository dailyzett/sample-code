const promiseGet = url => {
    return new Promise((resolve, reject) => {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', url);
        xhr.send();

        xhr.onload = () => {
            if (xhr.status === 200) {
                resolve(JSON.parse(xhr.response));
            } else {
                reject(xhr.status);
            }
        };
    });
};

const URL = 'https://jsonplaceholder.typicode.com'
promiseGet(`${URL}/posts/1`)
    .then(({userId}) => promiseGet(`${URL}/users/${userId}`))
    .then(userInfo => console.log(userInfo))
    .catch(err => console.error(err));

const githubURL = `https://api.github.com`;
const githubIds = ['jeresig', 'dailyzett'];
Promise.all(githubIds.map(id => promiseGet(`${githubURL}/users/${id}`)))
    .then(users => users.map(user => user.name))
    .then(console.log);

setTimeout(() => console.log(1), 0);

Promise.resolve()
    .then(() => console.log(2))
    .then(() => console.log(3));
const request = {
    post(url, payload) {
        return fetch(url, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(payload),
        });
    },
}

request.post(`${URL}/todos`, {
    userId: 1,
    title: 'JavaScript',
    completed: false
}).then(res => {
    return res.json();
}).then(todos => console.log(todos));