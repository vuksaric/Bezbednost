import { TestBed } from '@angular/core/testing';

import { AttackComponentService } from './attack-component.service';

describe('AttackComponentService', () => {
  let service: AttackComponentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AttackComponentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
