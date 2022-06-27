/*
 * Copyright 2016 ikidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mcxinyu.fragmentbackhandler

import androidx.fragment.app.*
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

public interface BackHandlerCallbacks {
    fun onBackPressed(): Boolean
}

object BackHandlerHelper {
    /**
     * 将back事件分发给 FragmentManager 中管理的子Fragment，如果该 FragmentManager 中的所有Fragment都
     * 没有处理back事件，则尝试 FragmentManager.popBackStack()
     *
     * @return 如果处理了back键则返回 **true**
     * @see .handleBackPress
     * @see .handleBackPress
     */
    fun handleBackPress(fragmentManager: FragmentManager): Boolean {
        val fragments: List<Fragment> = fragmentManager.fragments
        for (i in fragments.indices.reversed()) {
            val child: Fragment = fragments[i]
            if (isFragmentBackHandled(child)) {
                return true
            }
        }
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return true
        }
        return false
    }

    /**
     * 将back事件分发给Fragment中的子Fragment,
     * 该方法调用了 [.handleBackPress]
     *
     * @return 如果处理了back键则返回 **true**
     */
    fun handleBackPress(fragment: Fragment): Boolean {
        return handleBackPress(fragment.childFragmentManager)
    }

    /**
     * 将back事件分发给Activity中的子Fragment,
     * 该方法调用了 [.handleBackPress]
     *
     * @return 如果处理了back键则返回 **true**
     */
    fun handleBackPress(fragmentActivity: FragmentActivity): Boolean {
        return handleBackPress(fragmentActivity.supportFragmentManager)
    }

    /**
     * 将back事件分发给ViewPager中的Fragment,[.handleBackPress] 已经实现了对ViewPager的支持，所以自行决定是否使用该方法
     *
     * @return 如果处理了back键则返回 **true**
     * @see .handleBackPress
     * @see .handleBackPress
     * @see .handleBackPress
     */
    fun handleBackPress(viewPager: ViewPager?): Boolean {
        if (viewPager == null) return false
        val adapter: PagerAdapter = viewPager.adapter ?: return false
        val currentItem: Int = viewPager.currentItem
        val fragment: Fragment? =
            when (adapter) {
                is FragmentPagerAdapter -> adapter.getItem(currentItem)
                is FragmentStatePagerAdapter -> adapter.getItem(currentItem)
                else -> null
            }
        return isFragmentBackHandled(fragment)
    }

    /**
     * 判断Fragment是否处理了Back键
     *
     * @return 如果处理了back键则返回 **true**
     */
    fun isFragmentBackHandled(fragment: Fragment?): Boolean {
        return (fragment != null && fragment.isVisible
                && fragment.userVisibleHint //for ViewPager
                && fragment is BackHandlerCallbacks
                && (fragment as BackHandlerCallbacks).onBackPressed())
    }
}