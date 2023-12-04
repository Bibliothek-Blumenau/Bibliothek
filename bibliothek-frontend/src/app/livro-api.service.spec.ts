import { TestBed } from '@angular/core/testing';

import { LivroApiService } from './livro-api.service';

describe('LivroApiService', () => {
  let service: LivroApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LivroApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
