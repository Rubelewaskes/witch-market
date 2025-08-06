CREATE TYPE order_status AS ENUM ('IN_PROGRESS', 'COMPLETED', 'CANCELLED');
CREATE TYPE task_status AS ENUM ('PENDING', 'IN_PROGRESS', 'COMPLETED', 'FAILED');
CREATE TYPE worker_status AS ENUM ('ACTIVE', 'VACATION', 'WEEKEND');
CREATE TYPE specialization AS ENUM ('POTION_MASTER', 'ARTIFACTOR', 'SCHOLAR', 'CHIMEROLOGIST');

CREATE TABLE pipeline_definitions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    specializations specialization[] NOT NULL,
    steps INTEGER NOT NULL CHECK (steps > 0),
    requires_handoff BOOLEAN NOT NULL
);

CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    pipeline_id UUID REFERENCES pipeline_definitions(id),
    status order_status NOT NULL,
    client_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP
);

CREATE TABLE workers (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    specialization specialization NOT NULL,
    status worker_status NOT NULL,
    last_activity TIMESTAMP,
    thread_pool_size INTEGER NOT NULL DEFAULT 1 CHECK (thread_pool_size > 0)
);

CREATE TABLE task_executions(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    step_id INTEGER CHECK (step_id > 0),
    specialization specialization NOT NULL,
    status task_status NOT NULL,
    worker_id UUID NULL REFERENCES workers(id),
    started_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP,
    result_data JSONB
);