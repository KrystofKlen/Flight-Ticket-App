import { TestBed } from '@angular/core/testing';

import { PassServService } from './pass-serv.service';

describe('PassServService', () => {
  let service: PassServService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PassServService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
