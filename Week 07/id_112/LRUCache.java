package datastruct.lruCach;

import java.util.Hashtable;

/**
 * 146. LRU缓存机制
 * 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制。它应该支持以下操作： 获取数据 get 和 写入数据 put 。

 获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
 写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，它应该在写入新数据之前删除最近最少使用的数据值，从而为新的数据值留出空间。

 进阶:

 你是否可以在 O(1) 时间复杂度内完成这两种操作？

 示例:

 LRUCache cache = new LRUCache( 2 /缓存容量 / );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // 返回  1
        cache.put(3, 3);    // 该操作会使得密钥 2 作废
        cache.get(2);       // 返回 -1 (未找到)
        cache.put(4, 4);    // 该操作会使得密钥 1 作废
        cache.get(1);       // 返回 -1 (未找到)
        cache.get(3);       // 返回  3
        cache.get(4);       // 返回  4
 *
 */
public class LRUCache {

    /**
     * 哈希表 + 双向链表
     *
     * 想法

        这个问题可以用哈希表，辅以双向链表记录键值对的信息。
        所以可以在 O(1)O(1) 时间内完成 put 和 get 操作，
        同时也支持 O(1)O(1) 删除第一个添加的节点。
     */
    private Hashtable<Integer, DLinkedNode> cache =
            new Hashtable<Integer, DLinkedNode>();
    private int size;
    private int capacity;
    private DLinkedNode head, tail;


    class DLinkedNode {
        int key;
        int value;
        DLinkedNode pre;
        DLinkedNode next;
    }

    private void addNode(DLinkedNode node) {
        // always add the new node right after head
        node.pre = head;
        node.next = head.next;

        head.next.pre = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        DLinkedNode prev = node.pre;
        DLinkedNode next = node.next;

        prev.next = next;
        next.pre = prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addNode(node);
    }

    private DLinkedNode popTail() {
        DLinkedNode res = tail.pre;
        removeNode(res);
        return res;
    }

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;

        head = new DLinkedNode();
        // head.prev = null;

        tail = new DLinkedNode();
        // tail.next = null;

        head.next = tail;
        tail.pre = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) return - 1;

        // move the accessed node to the head;
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);

        if (node == null) {
            DLinkedNode newNode = new DLinkedNode();
            newNode.key = key;
            newNode.value = value;

            cache.put(key,newNode);
            addNode(newNode);
            ++size;

            if (size > capacity) {
                // pop the tail
                DLinkedNode tail = popTail();
                cache.remove(tail.key);
                --size;
            }
        } else {
            // update the value
            node.value = value;
            moveToHead(node);
        }
    }
    /**
     * LRUCache 对象会以如下语句构造和调用:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */
}
