import readline from 'readline/promises';
import { stdin as input, stdout as output } from 'node:process';

const rl = readline.createInterface({ input, output });

const menu = {
    아메리카노: 2000,
    라떼: 2500,
    카푸치노: 2700,
    바닐라라떼: 3000,
};

console.log('메뉴: ');
// Object.entries(menu)    객체를 [키, 값] 쌍의 배열로 바꾼다.
Object.entries(menu).forEach(([name, price]) => {
    console.log(`${name}: ${price}원`);
});

rl.question('주문할 음료는? ')
.then((item) => {
    if(menu[item]) {
        console.log(`${item} 주문 완료! 가격은 ${menu[item]}원입니다.`);
    } else {
        console.log('메뉴에 없는 항목입니다.');
    }
})
.catch((err) => {
    console.error('입력 오류: ', err.message);
})
.finally(() => rl.close());