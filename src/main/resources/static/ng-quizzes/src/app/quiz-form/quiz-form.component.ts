import {Component, EventEmitter, Output} from '@angular/core';
import {Quiz} from '../models/quiz';

@Component({
  selector: 'app-quiz-form',
  templateUrl: './quiz-form.component.html',
  styleUrls: ['./quiz-form.component.scss']
})
export class QuizFormComponent {
  @Output() add: EventEmitter<Quiz> = new EventEmitter<Quiz>();
  model = new Quiz(0, '', '', 0);
  addQuiz() {
    this.add.emit(this.model);
  }
}
