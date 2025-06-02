import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BarraBuscaComponent } from './search-bar.component';

describe('BarraBuscaComponent', () => {
  let component: BarraBuscaComponent;
  let fixture: ComponentFixture<BarraBuscaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BarraBuscaComponent]
    });
    fixture = TestBed.createComponent(BarraBuscaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
