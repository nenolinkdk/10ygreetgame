(() => {
  "use strict";

  const totalQuestions = 10;
  const scoreEl = document.getElementById("score");
  const progressEl = document.getElementById("progress");
  const startScreen = document.getElementById("startScreen");
  const quizScreen = document.getElementById("quizScreen");
  const finalScreen = document.getElementById("finalScreen");
  const questionLabel = document.getElementById("questionLabel");
  const questionText = document.getElementById("questionText");
  const answersEl = document.getElementById("answers");
  const finalScore = document.getElementById("finalScore");
  const startBtn = document.getElementById("startBtn");
  const againBtn = document.getElementById("againBtn");
  const installBtn = document.getElementById("installBtn");
  const installHint = document.getElementById("installHint");

  let score = 0;
  let questionNumber = 0;
  let currentQuestion = null;
  let deferredInstallPrompt = null;

  function showScreen(screen) {
    [startScreen, quizScreen, finalScreen].forEach((item) => item.classList.remove("active"));
    screen.classList.add("active");
  }

  function startGame() {
    score = 0;
    questionNumber = 0;
    updateScore();
    nextQuestion();
  }

  function updateScore() {
    scoreEl.textContent = String(score);
    progressEl.textContent = `${Math.min(questionNumber, totalQuestions)}/${totalQuestions}`;
  }

  function nextQuestion() {
    if (questionNumber === totalQuestions) {
      showFinal();
      return;
    }

    questionNumber += 1;
    currentQuestion = makeQuestion();
    questionLabel.textContent = `Spørgsmål ${questionNumber} af ${totalQuestions}`;
    questionText.textContent = currentQuestion.text;
    answersEl.replaceChildren();

    currentQuestion.choices.forEach((choice) => {
      const button = document.createElement("button");
      button.type = "button";
      button.textContent = String(choice);
      button.addEventListener("click", () => chooseAnswer(choice));
      answersEl.append(button);
    });

    updateScore();
    showScreen(quizScreen);
  }

  function chooseAnswer(answer) {
    if (answer === currentQuestion.answer) {
      score += 10;
    }
    updateScore();
    nextQuestion();
  }

  function showFinal() {
    finalScore.textContent = `Du fik ${score} point ud af ${totalQuestions * 10}.`;
    progressEl.textContent = `${totalQuestions}/${totalQuestions}`;
    showScreen(finalScreen);
  }

  function makeQuestion() {
    const type = randomInt(0, 3);
    if (type === 0) return additionQuestion();
    if (type === 1) return subtractionQuestion();
    if (type === 2) return multiplicationQuestion();
    return divisionQuestion();
  }

  function additionQuestion() {
    const a = randomInt(8, 50);
    const b = randomInt(5, 48);
    return makeChoices(`Hvad er ${a} + ${b}?`, a + b);
  }

  function subtractionQuestion() {
    const answer = randomInt(5, 54);
    const b = randomInt(4, 45);
    return makeChoices(`Hvad er ${answer + b} - ${b}?`, answer);
  }

  function multiplicationQuestion() {
    const a = randomInt(2, 10);
    const b = randomInt(2, 10);
    return makeChoices(`Hvad er ${a} x ${b}?`, a * b);
  }

  function divisionQuestion() {
    const answer = randomInt(2, 10);
    const divisor = randomInt(2, 9);
    return makeChoices(`Hvad er ${answer * divisor} / ${divisor}?`, answer);
  }

  function makeChoices(text, answer) {
    const choices = new Set([answer]);

    while (choices.size < 3) {
      choices.add(Math.max(1, answer + randomInt(-10, 10)));
    }

    return {
      text,
      answer,
      choices: shuffle([...choices])
    };
  }

  function randomInt(min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }

  function shuffle(items) {
    for (let index = items.length - 1; index > 0; index -= 1) {
      const swapIndex = randomInt(0, index);
      [items[index], items[swapIndex]] = [items[swapIndex], items[index]];
    }
    return items;
  }

  window.addEventListener("beforeinstallprompt", (event) => {
    event.preventDefault();
    deferredInstallPrompt = event;
    installBtn.hidden = false;
    installHint.textContent = "Tryk Installer for at lægge spillet på telefonen.";
  });

  installBtn.addEventListener("click", async () => {
    if (!deferredInstallPrompt) {
      installHint.textContent = "Hvis boksen ikke åbner: tryk på de tre prikker i Chrome og vælg Installer app eller Føj til startskærm.";
      return;
    }
    deferredInstallPrompt.prompt();
    await deferredInstallPrompt.userChoice;
    deferredInstallPrompt = null;
    installHint.textContent = "Hvis appen ikke kom på skærmen: åbn Chrome-menuen og vælg Installer app.";
  });

  if ("serviceWorker" in navigator) {
    window.addEventListener("load", () => {
      navigator.serviceWorker.register("./sw.js").then((registration) => registration.update());
    });
  }

  startBtn.addEventListener("click", startGame);
  againBtn.addEventListener("click", startGame);
  updateScore();
})();
