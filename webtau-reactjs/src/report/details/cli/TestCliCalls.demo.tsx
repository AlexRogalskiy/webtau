/*
 * Copyright 2021 webtau maintainers
 * Copyright 2019 TWO SIGMA OPEN SOURCE, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import React from 'react';

import TestCliCalls from './TestCliCalls';
import { Registry, simulateState } from 'react-component-viewer';
import { WebTauTest } from '../../WebTauTest';

const [getUrlState, setUrlState] = simulateState({ cliCallIdxs: '0-1-2' });

export function testCliCallsDemo(registry: Registry) {
  registry.add('multiple cli calls', () => (
    <TestCliCalls test={createTestWithCliCalls()} urlState={getUrlState()} onInternalStateUpdate={setUrlState} />
  ));
  registry.add('with persona', () => (
    <TestCliCalls test={createTestWithCliCalls('Alice')} urlState={getUrlState()} onInternalStateUpdate={setUrlState} />
  ));
}

function createTestWithCliCalls(personaId?: string): WebTauTest {
  return {
    id: 'testid',
    containerId: '',
    elapsedTime: 0,
    scenario: 'my scenario',
    startTime: 1034343434,
    steps: [],
    warnings: [],
    cliCalls: [
      {
        command: 'ls -l',
        startTime: 100,
        exitCode: 0,
        elapsedTime: 300,
        out: 'line 1\nline 2\nline 3',
        outMatches: ['line 2'],
        err: 'line 4\nline 5',
        errMatches: ['line 5'],
        errorMessage: '',
        mismatches: [],
        config: {
          'workding dir': '/home/user/working/dir',
          $VAR: 'my_var_value',
          timeout: 2000,
        },
      },
      {
        personaId,
        command: 'ls -lr',
        startTime: 100,
        exitCode: 2,
        elapsedTime: 300,
        out: 'line 1\nline 2\nline 3',
        outMatches: ['line 2'],
        err: 'line 4\nline 5',
        errMatches: ['line 5'],
        errorMessage: '',
        mismatches: [
          'process output expect to contain "versian:"\nprocess output: mismatches:\n                \n                process output[0]:    actual string: welcome to my script\n                                   expected pattern: \\Qversian:\\E\n                process output[1]:    actual string: version: 12.43.2\n                                   expected pattern: \\Qversian:\\E',
        ],
        config: {},
      },
      {
        command: 'als -l',
        startTime: 100,
        exitCode: undefined,
        elapsedTime: 300,
        out: 'line 1\nline 2\nline 3',
        outMatches: ['line 2'],
        err: 'line 4\nline 5',
        errMatches: ['line 5'],
        errorMessage: 'Cannot run program "scripts/simplo": error=2, No such file or directory',
        mismatches: [],
        config: {},
      },
    ],
  };
}
