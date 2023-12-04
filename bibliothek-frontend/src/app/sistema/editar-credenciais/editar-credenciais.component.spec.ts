import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SetCredenciaisComponent } from './editar-credenciais.component';

describe('SetCredenciaisComponent', () => {
  let component: SetCredenciaisComponent;
  let fixture: ComponentFixture<SetCredenciaisComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SetCredenciaisComponent]
    });
    fixture = TestBed.createComponent(SetCredenciaisComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
