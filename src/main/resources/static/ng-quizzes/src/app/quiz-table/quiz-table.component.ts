import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Quiz} from '../models/quiz';
import {faEdit, faTimes} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-quiz-table',
  templateUrl: './quiz-table.component.html',
  styleUrls: ['./quiz-table.component.scss']
})
export class QuizTableComponent {
  @Input() quizzes: Quiz[];
  @Output() onRemove: EventEmitter<number> = new EventEmitter<number>();
  icons = {
    remove: faTimes,
    edit: faEdit
  };

  removeQuiz(id: number) {
    this.onRemove.emit(id);
  }
}
