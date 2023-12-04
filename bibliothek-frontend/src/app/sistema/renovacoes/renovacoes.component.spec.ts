import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RenovacoesComponent } from './renovacoes.component';

describe('RenovacoesComponent', () => {
  let component: RenovacoesComponent;
  let fixture: ComponentFixture<RenovacoesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RenovacoesComponent]
    });
    fixture = TestBed.createComponent(RenovacoesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
