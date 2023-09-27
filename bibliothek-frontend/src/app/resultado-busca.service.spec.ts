import { TestBed } from '@angular/core/testing';

import { ResultadoBuscaService } from './resultado-busca.service';

describe('ResultadoBuscaServiceService', () => {
  let service: ResultadoBuscaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResultadoBuscaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
