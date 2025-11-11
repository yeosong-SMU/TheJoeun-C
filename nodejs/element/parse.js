const readline = require('readline');

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
});

rl.question('숫자1: ', (a) => {
    rl.question('숫자2: ', (b) => {
        const sum = parseInt(a) + parseInt(b);
        console.log(`${a} + ${b} = ${sum}`);
        rl.close();
    });
});