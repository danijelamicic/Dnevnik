import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AdducenikComponent } from './adducenik.component';

describe('AdducenikComponent', () => {
  let component: AdducenikComponent;
  let fixture: ComponentFixture<AdducenikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AdducenikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AdducenikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
