# Input identity correction

This signed correction binds the unchanged audit verdict to the report's actual bytes. It corrects a one-character transcription error in the report hash embedded in `verdict.md`; neither original file was edited.

- Frozen input `c-middle-1203-1204-sprint-state/report.md`: SHA-256 `29bbfc038e49467298a5d040a1ad6e8377dc056eed2bf9652c7832ee3ddc4ccf` (rehash of its absolute path).
- Unchanged audit `c-middle-1203-1204-sprint-state-audit/verdict.md`: SHA-256 `bc68407198bf46a9f87949d9883b8ae31f1433f052504d91366dd01309a54cda` (rehash of its absolute path). Its input citation omitted the `a` in `...a1ad6e...`, spelling `...a1d6e...`.
- Correct binding: the `ACCEPT` verdict in that exact verdict file applies to the frozen report SHA-256 above and the evidence identity recorded in the verdict.

The scoped ordinary on-foot sprint-state no-difference conclusion still holds. I rehashed the five compared source files in both exact Mojmap trees; each pair remains byte-identical: `Input.java` `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb`; `KeyboardInput.java` `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0`; `LocalPlayer.java` `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5`; `Options.java` `39ae95ee0def943be3f9ada31c65b5c8a8a258d61fcad09701903bfd93c3f4fa`; `Entity.java` `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9`. The audit remains bounded to ordinary on-foot sprint-state input sampling, start/timer/stop gates, and sprint-flag writes; it does not freeze pair-wide coverage.

Signed: independent source audit agent (Codex), 2026-11-18.
