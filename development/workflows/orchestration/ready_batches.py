"""List the immutable publication refs that have not reached primary main."""

import argparse
import json
import os
from pathlib import Path
import subprocess
import sys


READY_PREFIX = 'refs/campaign-ready/'


def git(repo, *args):
    env = os.environ.copy()
    for key in (
        'GIT_DIR', 'GIT_COMMON_DIR', 'GIT_WORK_TREE', 'GIT_IMPLICIT_WORK_TREE',
        'GIT_OBJECT_DIRECTORY', 'GIT_ALTERNATE_OBJECT_DIRECTORIES', 'GIT_SHALLOW_FILE',
    ):
        env.pop(key, None)
    env.update(GIT_OPTIONAL_LOCKS='0', GIT_GRAFT_FILE=os.devnull)
    command = [
        'git', '--no-lazy-fetch', '--no-replace-objects', '-c', 'advice.graftFileDeprecated=false',
        '-C', str(repo), *args,
    ]
    result = subprocess.run(command, env=env, stdin=subprocess.DEVNULL, capture_output=True)
    # Git can warn about a broken ref and skip it while still returning success.
    if result.returncode or result.stderr:
        error = result.stderr.decode('utf-8', errors='replace').strip()
        raise ValueError(f'{repo}: git {" ".join(args)} exited {result.returncode}. {error}'.strip())
    return result.stdout.decode('utf-8', errors='surrogateescape')


def checkout(path):
    repo = Path(path).resolve()
    root = git(repo, 'rev-parse', '--show-toplevel').rstrip('\r\n')
    if Path(root).resolve() != repo:
        raise ValueError(f'Expected a Git checkout root at {repo}; found {root}')
    return repo


def pending_batches(primary_path, sources):
    primary = checkout(primary_path)
    if git(primary, 'rev-parse', '--is-shallow-repository').strip() != 'false':
        raise ValueError(f'Primary checkout needs complete history to check publication: {primary}')
    main = git(primary, 'rev-parse', '--verify', 'refs/heads/main^{commit}').strip()
    # Walking from main works even when a pending source commit is absent from its object store.
    published = set(git(primary, 'rev-list', '--no-abbrev-commit', main).splitlines())

    pending = []
    for name, path in sorted(sources):
        repo = checkout(path)
        refs = git(
            repo, 'for-each-ref', '--sort=refname',
            '--format=%(refname)%09%(objectname)%09%(objecttype)%09%(symref)', READY_PREFIX,
        )
        for row in refs.split('\n'):
            if not row:
                continue
            ref, commit, object_type, symbolic_target = row.rstrip('\r').split('\t')
            if object_type != 'commit' or symbolic_target:
                raise ValueError(f'{repo}: {ref} must point directly to a commit')
            if commit not in published:
                pending.append({
                    'source': name,
                    'path': str(repo),
                    'batch': ref.removeprefix(READY_PREFIX),
                    'ref': ref,
                    'commit': commit,
                })
    return {'main': main, 'pending': pending}


def main():
    parser = argparse.ArgumentParser(
        description=__doc__,
        epilog=(
            'Publish by preserving the exact commit on main. To correct an unpublished batch, '
            'withdraw its old ref and use a new batch name. Recheck the ref and commit before publishing.'
        ),
    )
    parser.add_argument('--primary', required=True, metavar='PATH', help='primary checkout root')
    parser.add_argument(
        '--source', nargs=2, action='append', required=True, metavar=('NAME', 'PATH'),
        help='named integration clone root. Repeat for each clone.',
    )
    args = parser.parse_args()
    names = [name for name, _ in args.source]
    if any(not name.strip() for name in names) or len(names) != len(set(names)):
        parser.error('source names must be nonempty and unique')
    try:
        result = pending_batches(args.primary, args.source)
    except (OSError, ValueError) as error:
        parser.exit(1, f'{parser.prog}: {error}\n')
    print(json.dumps(result, separators=(',', ':')))


if __name__ == '__main__':
    main()
