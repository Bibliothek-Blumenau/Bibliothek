import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Recommendationsomponent } from './recommendations.component';

describe('RecomendacoesComponent', () => {
  let component: Recommendationsomponent;
  let fixture: ComponentFixture<Recommendationsomponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [Recommendationsomponent]
    });
    fixture = TestBed.createComponent(Recommendationsomponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
