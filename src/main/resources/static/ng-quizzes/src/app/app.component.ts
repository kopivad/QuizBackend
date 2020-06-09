import { Component } from '@angular/core';
import {Quiz} from './models/quiz';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  quizzes = [
    new Quiz(1, 'Ivanka', 'Math description', 100),
    new Quiz(2, 'Math', 'Math description', 100),
    new Quiz(3, 'Math', 'Math description', 100),
    new Quiz(4, 'Math', 'Math description', 100)
  ];

  addQuiz(quiz: Quiz) {
    const newQuiz: Quiz = new Quiz(this.quizzes.length + 1, quiz.title, quiz.description, quiz.total);
    this.quizzes.push(newQuiz);
  }

  removeQuiz(id: number) {
    this.quizzes = this.quizzes.filter(q => q.id !== id);
    console.log(id);
  }
}
