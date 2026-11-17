# Independent bounded source audit — ACCEPT

Pair: `1.11.2→1.12`. Candidate `bed-bounce-collision-response.md` SHA-256 `30B0194E7B5AC3B4DA60E0B6A3A97FCB853774F1D7609E919D552F4FEFD56D4A` verified.

## Source identity

1.11.2 ready marker `CEABFB266993567AAB8C867525365E18AA0A0D7BA5A2CB7B6CFCC83D21318C40`; manifest `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0`; provenance `DCDB65E1361297A584C5B93F581B1B539911911C6CEC1A203740BAA5379732C6`. 1.12 mapping-local provenance `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467`. Source rows match: BedBlock `84F56AAB19ADAE4B953A27040E5F431F65C8923D6BD169995AC5A5F629A024E1` / `48C127B2DF425CB8D7FF60A284207DE10A8408E90EE18A8449522A004A0B4369`; Block `AB873436B24487AB0EE8055BF7A478349C7F7978398AEA8E387796A6C820996A` / `E4A90ECA411E7B0E14F5018F7385EC29891F6CDF1A1E917FA648B5712F9B33A1`; Entity `CE8104A17CE783DF639CF9726E7B1CD0936563EAA7BA308603335BBE05D49440` / `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A`.

## Verdict

Both Entity.move versions call `beforeCollision` when vertical displacement clips (`o != y`). A 1.11.2 bed inherits Block's zero-velocity hook. In 1.12 a bed delegates to that zeroing hook when sneaking; otherwise, negative vertical velocity is inverted and multiplied by `0.66F`. The extra `0.8` multiplier is only for nonliving entities, so not for a player. ACCEPT strictly for a falling, non-sneaking player on vertical clipping against a bed. Bed-entry eligibility and other contacts are excluded. This is a source response claim, not pair coverage or runtime/trajectory evidence.
