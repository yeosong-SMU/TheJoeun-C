import readline from 'readline/promises';
import { stdin as input, stdout as output } from 'node:process';

const rl = readline.createInterface({ input, output });

const dict = {
    apple: "사과",
    banana: "바나나",
    orange: "오렌지",
    cat: "고양이",
    dog: "개"
};

rl.question("영어 단어를 입력하세요: ")
.then ((word) => {
    const meaning = dict[word.toLowerCase()];
    if(meaning) {
        console.log(`${word}의 뜻은 "${meaning}"입니다.`);
    }else {
        console.log("사전에 없는 단어입니다.");
    }
})
.catch((err) => {
    console.error("입력 처리 중 오류 발생: ", err.message);
})
.finally(() => rl.close());