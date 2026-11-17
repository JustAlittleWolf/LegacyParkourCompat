# Unattended movement campaign

Start only on the user's start signal. Continue until the user sends stop. There is no elapsed-time cutoff. A checkpoint ends a batch and immediately opens the next. On stop, propagate it to descendants immediately and report the latest durable state.

## Delegation

Launch one GPT-6.2 Sol high master and three GPT-6.2 Sol medium leads with delegation rights and fresh contexts. Each lead may use up to 24 workers within actual shared host capacity. Share available slots according to ready work and the bottleneck. On a host with 25 shared subagent slots, three leads leave up to 22 worker slots. If nested delegation is unavailable, leads prepare assignments and the master dispatches the workers directly within the same capacity.

Use English for chat titles, goal objectives, prompts, worker communication, evidence, commit messages and reports. Arm a native goal in each of the four chats at start. Each lead follows the same block reservation procedure in the [orchestration workflow](WORKFLOW.md#reserve-an-end-to-end-block). All three may select from the full backlog and carry their blocks through source evidence, independent acceptance, candidate implementation and branch integration. Give each lead a separate checkout and branch. The master confirms reservations, owns central records and publishes completed batches to main in sequence.

Dispatch routine workers with explicit native arguments `model="gpt-6-luna"`, `reasoning_effort="high"` and `fork_turns="none"`. Verify execution on the first assignment. An omitted model suggestion alone does not establish unavailability. If the call fails, hold routine dispatch and report the error to the coordinator. This run requires Luna for routine work. Resolve technical issues within the owning group and reassign capacity around unavailable resources or approvals. Follow the [reusable orchestration procedure](WORKFLOW.md) for queue balancing, Git recovery, checkpoints and stop propagation.

## First dispatch and checks

Read the live backlog, latest evidence and checkout ownership. Start from accepted work awaiting product reconciliation and bounded coverage gaps that can close this run. Recheck recorded active ownership against live workers before assigning. Classify unexplained local edits as user-owned. Establish the first real evidence commit and first eligible product commit through their respective owners. Continue after both checkpoints.

Defer standalone product code reviews until project end. The integrator checks fidelity and wiring during integration and runs the prescribed test-disabled build on coherent batches. Keep independent source acceptance and full-pair audit gates. Tests, runtime launches, Docker restarts and remote pushes require separate authorization.
