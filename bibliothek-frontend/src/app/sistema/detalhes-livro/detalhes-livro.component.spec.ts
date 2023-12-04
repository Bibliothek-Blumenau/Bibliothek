import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DetalhesLivroComponent } from './detalhes-livro.component';

describe('DetalhesLivroComponent', () => {
  let component: DetalhesLivroComponent;
  let fixture: ComponentFixture<DetalhesLivroComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DetalhesLivroComponent]
    });
    fixture = TestBed.createComponent(DetalhesLivroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
