package localThreadPoolDemo;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GeneralCached extends TestDao {
    /*
    适合读多写少
    未实现缓存容量
    未实现缓存过期
    只适合单机
    并发低，只用一把锁
    更新方法不佳，应该按锁分区
     */
    TestDao dao = new TestDao();
    Map<SqlPair, String> map = new HashMap<>();
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    @Override
    public List<String> selectList() {
        return super.selectList();
    }

    @Override
    public String selectOne(String k) {
        readWriteLock.readLock().lock();
        SqlPair key = new SqlPair(k, null);

        try {
            String o = map.get(key);
            if (o != null) {
                return o;
            }

        } finally {
            readWriteLock.readLock().unlock();
        }

        readWriteLock.writeLock().lock();
        try {
            String value = map.get(key);
            if (value == null) {
                value = dao.selectOne(k);
                map.put(key, value);
            }
            return value;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public int updateOne(String a) {
        readWriteLock.writeLock().lock();
        try {
            dao.updateOne(a);
            map.remove(a);
            return 1;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private class SqlPair {
        String sql;
        Object[] args;

        public SqlPair(String sql, Object[] args) {
            this.sql = sql;
            this.args = args;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SqlPair sqlPair = (SqlPair) o;
            return Objects.equals(sql, sqlPair.sql) &&
                    Arrays.equals(args, sqlPair.args);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(sql);
            result = 31 * result + Arrays.hashCode(args);
            return result;
        }
    }
}


class TestDao {
    public List<String> selectList() {
        return null;
    }

    public String selectOne(String k) {
        return null;
    }

    public int updateOne(String a) {
        return 1;
    }
}
