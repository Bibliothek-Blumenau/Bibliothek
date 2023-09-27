import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TodosOsLivrosComponent } from './todos-os-livros.component';

describe('TodosOsLivrosComponent', () => {
  let component: TodosOsLivrosComponent;
  let fixture: ComponentFixture<TodosOsLivrosComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TodosOsLivrosComponent]
    });
    fixture = TestBed.createComponent(TodosOsLivrosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
