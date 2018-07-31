import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PogledroditeljComponent } from './pogledroditelj.component';

describe('PogledroditeljComponent', () => {
  let component: PogledroditeljComponent;
  let fixture: ComponentFixture<PogledroditeljComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PogledroditeljComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PogledroditeljComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
